
[WorkbenchPluginAttribute("Start debug Client", "Just for testing", "alt+2", "", {"ScriptEditor"})]
class StartDebugAll : DayZTool
{
	override void Run()
	{
		RunDayZBat("P:/dayzworkbenchtool/StartDebugClient.bat");
	}
}
