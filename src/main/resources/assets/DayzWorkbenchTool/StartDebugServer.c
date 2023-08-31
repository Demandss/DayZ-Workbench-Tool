
[WorkbenchPluginAttribute("Start debug Server", "Just for testing", "alt+3", "", {"ScriptEditor"})]
class StartDebugAll : DayZTool
{
	override void Run()
	{
		RunDayZBat("P:/dayzworkbenchtool/StartDebugServer.bat");
	}
}
