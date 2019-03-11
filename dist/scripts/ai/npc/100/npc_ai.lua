function onTalk(PlayableCharacter, dialog)
    dialog = dialog or "index"
    print("Trigger on Talk")
    print(PlayableCharacter:getName())
    print("Player count:" .. World():getAllPlayers():size())
    print("dir" .. self_path)
    print(dialog)
    if dialog == "index" then
        prepareDialogAndSend(PlayableCharacter, renderDefaultDialog())
    end

end

function renderDefaultDialog()
    local dialog = ReadFile(self_path .. "/dialogs/index.dialog")
    dialog = string.gsub(dialog, "$PlayersOnline", World():getAllPlayers():size())
    return dialog
end

function prepareDialogAndSend(PlayableCharacter, dialog)
    dialog = string.gsub(dialog, "$npc_id", npc:getObjectId())
    PlayableCharacter:sendDialog(dialog)
end
