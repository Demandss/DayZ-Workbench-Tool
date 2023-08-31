
[WorkbenchPluginAttribute("Start debug Client/Server", "Just for testing", "alt+1", "", {"ScriptEditor"})]
class StartDebugAll : DayZTool
{
	override void Run()
	{
		RunDayZBat("P:/dayzworkbenchtool/StartDebugAll.bat");
	}
}
