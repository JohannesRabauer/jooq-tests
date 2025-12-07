@echo off
setlocal

echo Initializing development environment...

echo Starting Postgres through docker compose...
docker compose up -d --remove-orphans

if errorlevel 1 (
    echo Error: Unable to start docker compose.
    exit /b 1
)

echo Waiting for Postgres to become available...

set "DB_HOST=localhost"
set "DB_PORT=5432"

:wait_for_postgres
powershell -Command ^
  "$tcp = New-Object Net.Sockets.TcpClient; " ^
  "try { $tcp.Connect('%DB_HOST%', %DB_PORT%) } catch {} ; " ^
  "if ($tcp.Connected) { exit 0 } else { exit 1 }"

if %ERRORLEVEL%==0 (
    echo Postgres is ready.
) else (
    echo Still waiting...
    timeout /t 2 >nul
    goto wait_for_postgres
)

echo Running Flyway migrations...
mvn -q flyway:migrate

if errorlevel 1 (
    echo Error during Flyway migrations.
    exit /b 1
)

echo Running jOOQ code generation...
mvn -q generate-sources

if errorlevel 1 (
    echo Error during jOOQ code generation.
    exit /b 1
)

echo Development environment successfully set up.
echo You can now run:
echo mvn quarkus:dev

endlocal
exit /b 0
