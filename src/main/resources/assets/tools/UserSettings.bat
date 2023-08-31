@echo off
SET "GAME_PATH=&GAME_PATH&"
SET "WORKDRIVE_PATH=&WORKDRIVE_PATH&"
SET "WORKBENCH_PATH=&WORKBENCH_PATH&"

SET "WORKSHOP_PATH=%WORKDRIVE_PATH%Workshop"

SET "LOAD_MODS=&LOAD_MODS&"

SET "MODS_CLIENT=&MODS_CLIENT&"
SET "MODS_SERVER=&MODS_SERVER&"

SET ServerArgs= &SERVER_ARGS& -servermod=%MODS_SERVER% "-mod=%MODS_CLIENT%"
SET ClientArgs= &CLIENT_ARGS& "-mod=%MODS_CLIENT%"
SET ClientSandboxArgs= &CLIENT_SANDBOX_ARGS& "-mod=%MODS_CLIENT%"