package fun.demc.fireball;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class YieldCommand implements TabExecutor {
    ThrowableFireball instance = ThrowableFireball.getInstance();
    public boolean onCommand(CommandSender sender, Command cmd, String label, String @NonNull [] args) {

        try{
            if (args.length == 0) {//错误输入
                sender.sendMessage(ChatColor.RED + Lang.getMessage("noArg"));
                return true;
            } else if (args.length == 1) {//help or enable or disable
                if (args[0].equals("help")) {
                    sender.sendMessage(ChatColor.YELLOW + Lang.getMessage("usage"));
                    return true;
                } else if (args[0].equals("on")) {
                    if (instance.isEnabled_()) {
                        sender.sendMessage(ChatColor.RED + Lang.getMessage("alreadyEnabled"));
                        return true;
                    } else {
                        sender.sendMessage(ChatColor.YELLOW + Lang.getMessage("notEnabled"));
                        instance.setEnabled_(true);
                        return true;
                    }
                } else if (args[0].equals("off")) {
                    if (!instance.isEnabled_()) {
                        sender.sendMessage(ChatColor.RED + Lang.getMessage("alreadyDisabled"));
                        return true;
                    } else {
                        sender.sendMessage(ChatColor.YELLOW + Lang.getMessage("notDisabled"));
                        instance.setEnabled_(false);
                        return true;
                    }
                }
            } else if (args.length == 2) {
                if (!args[0].equals("setyield")) {
                    sender.sendMessage(ChatColor.RED + Lang.getMessage("noArg"));
                    return true;
                }
                float yield;
                try {
                    yield = Float.parseFloat(args[1]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + Lang.getMessage("yieldRequirement"));
                    return true;
                }
                if (yield < 0 || yield > 100) {
                    sender.sendMessage(ChatColor.RED + Lang.getMessage("yieldRequirement"));
                    return true;
                }
                instance.setYield(yield);
                sender.sendMessage(ChatColor.YELLOW + Lang.getMessage("set") + yield);
                return true;
            }
            sender.sendMessage(ChatColor.RED + Lang.getMessage("noArg"));
            return true;
        }catch(CommandException e){
            instance.getLogger().warning(e.getMessage());
            sender.sendMessage(ChatColor.RED + Lang.getMessage("pluginNotEnabled"));
            return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (args.length == 1) {
            suggestions.add("on");
            suggestions.add("off");
            suggestions.add("setyield");
            suggestions.add("help");
        }
        if (args.length == 2 && args[0].equals("setyield")) {
            suggestions.add(Lang.getMessage("suggestion"));
        }
        return suggestions;
    }

}