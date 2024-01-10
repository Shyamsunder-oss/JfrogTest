@echo off
pushd %~dp0
set script_dir=%CD%
popd
start cmd /c "regsvr32 %script_dir%\Configuration\libs\tools\AutoItX3_x64.dll"