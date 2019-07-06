package Enchanter.Minor;

import Enchanter.Properties;
import Enchanter.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Equipment;
import org.powerbot.script.rt4.Game;

import static Superior.Utilities.random;

public class Staff extends Task<ClientContext> {

    public Staff(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        if (ctx.equipment.itemAt(Equipment.Slot.MAIN_HAND).id() == Properties.SelectedItem.SELECTED_ITEM.necStaff) {
            Properties.rightStaff = true;
            return false;
        } else {
            Properties.rightStaff = false;
            return true;
        }
    }

    @Override
    public void execute() {
        int staff = Properties.SelectedItem.SELECTED_ITEM.necStaff;
        if(ctx.game.tab() != Game.Tab.INVENTORY){
            ctx.game.tab(Game.Tab.INVENTORY);
            Condition.wait(() -> ctx.game.tab() == Game.Tab.INVENTORY, random(100), 5);
        } else if (ctx.magic.casting(Properties.SelectedItem.SELECTED_ITEM.spell)) {
            ctx.inventory.poll().interact("Cancel");
            Condition.wait(() -> ctx.magic.casting(Properties.SelectedItem.SELECTED_ITEM.spell),random(80), 10);
        } else if(!ctx.inventory.select().id(staff).isEmpty() && !ctx.bank.opened()) {
            ctx.inventory.select().id(staff).poll().interact("Wield");
            Condition.wait(() -> ctx.equipment.itemAt(Equipment.Slot.MAIN_HAND).id() == staff, random(100), 10);
        } else if(!ctx.bank.opened()) {
            ctx.bank.open();
            Condition.wait(() -> ctx.bank.opened(), random(90), 5);
        } else if(ctx.inventory.isFull()) {
            ctx.bank.depositAllExcept(Properties.cosmicRuneId);
            Condition.wait(() -> !ctx.inventory.isFull(), random(80), 5);
        } else if(ctx.bank.opened() && ctx.inventory.select().id(staff).isEmpty()) {
            ctx.bank.withdraw(staff, 1);
            Condition.wait(() -> !ctx.inventory.select().id(staff).isEmpty(), random(60), 5);
        } else if(ctx.bank.opened() && !ctx.inventory.select().id(staff).isEmpty()) {
            ctx.bank.close();
            Condition.wait(() -> !ctx.bank.opened(), random(60), 5);
        }
    }
}
