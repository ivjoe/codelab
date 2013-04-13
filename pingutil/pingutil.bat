:: usage pingutil.bat hosts
@echo off

echo -- %DATE% %TIME% >> %1.log
for /F %%i in (%1) do (
    ping -n 1 %%i | findstr "Reply from" > NUL
    if errorlevel==1 (
        echo %%i BAD
        echo %%i BAD >> %1.log
    ) else (
        echo %%i OK
        echo %%i OK >> %1.log
    )
)
echo.>> %1.log
