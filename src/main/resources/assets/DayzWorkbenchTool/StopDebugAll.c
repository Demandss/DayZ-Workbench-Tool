
[WorkbenchPluginAttribute("Start debug Client/Server", "Just for testing", "alt+2", "", {"ScriptEditor"})]
class StartDebugAll : DayZTool
{
	override void Run()
	{
		Workbench.RunCmd("cmd /c P:/dayzworkbenchtool/StopDebugAll.bat");
	}
}