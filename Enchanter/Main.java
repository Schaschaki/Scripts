package Enchanter;

import Enchanter.Minor.Staff;
import Enchanter.UI.Interface;
import org.powerbot.script.Condition;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Superior.Utilities.random;

@Script.Manifest(name = "Enchanter", properties = "author=Schaschaki; topic=739123; client=4;", description = "Enchanting")
public class Main extends PollingScript<ClientContext>  {


    //Couldn't think of a better solution than making it static, so I can remove Task staff from within the class Staff
    //feel free to tell me if you have something in mind

    private List<Task> taskList = new ArrayList<>();
    private int startingExp, endingExp;

    @Override
    public void start() {
        taskList.addAll(Arrays.asList(new Enchant(ctx), new Bank(ctx), new Staff(ctx)));
        new Interface();
        startingExp = ctx.skills.experience(Constants.SKILLS_MAGIC);
        System.out.println("starting exp: " + startingExp);
    }

    @Override
    public void poll() {
        while(Properties.window);
        Condition.wait(() -> Properties.running, random(100),10);
        if(Properties.running) {
            for(Task task : taskList) {
                if(task.activate()){
                    task.execute();
                }
            }
        } else ctx.controller.stop();
    }

    @Override
    public void stop() {
        endingExp  = ctx.skills.experience(Constants.SKILLS_MAGIC);
        System.out.println("starting xp: " + startingExp);
        System.out.println("ending xp: " + endingExp);
        int gainedExp = endingExp - startingExp;
        int enchantedItems = Properties.calcEnchantedItemCount(gainedExp);
        JOptionPane.showMessageDialog(null,
                "During the runtime " + gainedExp + " exp was gathered" + "\n A total of "
                        + enchantedItems + " items were enchanted");
    }
}
