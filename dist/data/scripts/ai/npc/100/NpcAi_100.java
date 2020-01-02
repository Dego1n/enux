import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.scripting.ai.npc.NpcAi;

public class NpcAi_100 extends NpcAi{

    public NpcAi_100() {
        super(100);
    }

    public void onTalk(PlayableCharacter pc, String dialog)
    {
        switch(dialog)
        {
            case "platform_info":
                prepareDialogAndSend(pc,renderPlatformDialog());
                break;
            case "buy_list":
                pc.showBuyList(1);
                break;
            default:
                super.onTalk(pc, dialog);
        }
    }
    private String renderPlatformDialog()
    {
        String dialog = getDialog("platform_info.dialog");

        return dialog
                .replace("$java_ver", System.getProperty("java.version"))
                .replace("$os_name", System.getProperty("os.name"))
                .replace("$os_ver", System.getProperty("os.version"))
                .replace("$os_arch", System.getProperty("os.arch"));
    }
}