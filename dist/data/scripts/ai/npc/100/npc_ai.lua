function onTalk(PlayableCharacter, dialog)
    dialog = dialog or "index"
    if dialog == "index" then
        prepareDialogAndSend(PlayableCharacter, renderDefaultDialog())
    end

end

function Dialog(PlayableCharacter, dialog)
    if dialog == "platform_info" then
        prepareDialogAndSend(PlayableCharacter, renderPlatformDialog())
    elseif dialog == "buy_list" then
        sendBuyList(PlayableCharacter, 1)
    else
        prepareDialogAndSend(PlayableCharacter, renderDefaultDialog())
    end
end

function renderDefaultDialog()
    local dialog = ReadFile(self_path .. "/dialogs/index.dialog")
    dialog = string.gsub(dialog, "$PlayersOnline", World():getAllPlayers():size())
    return dialog
end

function renderPlatformDialog()
    local dialog = ReadFile(self_path .. "/dialogs/platform_info.dialog")
    dialog = string.gsub(dialog, "$java_ver", SysProps("java.version"))
    dialog = string.gsub(dialog, "$os_name", SysProps("os.name"))
    dialog = string.gsub(dialog, "$os_ver", SysProps("os.version"))
    dialog = string.gsub(dialog, "$os_arch", SysProps("os.arch"))
    return dialog
end

function prepareDialogAndSend(PlayableCharacter, dialog)
    dialog = string.gsub(dialog, "$npc_id", npc:getObjectId())
    PlayableCharacter:sendDialog(dialog)
end


function sendBuyList(pc, buyListId)
    pc:showBuyList(buyListId)
end