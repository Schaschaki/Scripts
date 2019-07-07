package Enchanter;

import Enchanter.Minor.Staff;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;

import static Superior.Utilities.random;

public class Bank extends Task<ClientContext> {
    Bank(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() { //either no runes, no Items or bank open
        if(!Properties.rightStaff || ctx.magic.casting(Properties.SelectedItem.SELECTED_ITEM.spell)) return false;
        else return ctx.inventory.select().id(Properties.SelectedItem.SELECTED_ITEM.id()).isEmpty()
                    || ctx.inventory.select().id(Properties.cosmicRuneId).isEmpty()
                    || ctx.bank.opened();
    }

    @Override
    public void execute() {
        int id = Properties.SelectedItem.SELECTED_ITEM.id();

         if(!ctx.bank.opened()) {                                                           //check if the bank is opened
            ctx.bank.open();
            Condition.wait(()-> ctx.bank.opened(), 80, 10);
        } else {                                                                            //should be opened now
            if(ctx.inventory.select().id(id).isEmpty()
                    && (ctx.inventory.select().count() - ctx.inventory.select().id(Properties.cosmicRuneId).count()) > 0
                    || ctx.inventory.select().id(Properties.cosmicRuneId).isEmpty()
                    && !ctx.inventory.select().isEmpty()) {
                ctx.bank.depositAllExcept(Properties.cosmicRuneId);


            } else if (ctx.inventory.select().id(Properties.cosmicRuneId).isEmpty()) {        //check if runes in inventory
                if(ctx.bank.select().id(Properties.cosmicRuneId).isEmpty()) {               //check if runes in bank
                    System.err.println("No more Runes available");
                    Properties.running = false;
                } else {                                                                    //withdraw runes if no runes in inventory but in bank
                    ctx.bank.withdraw(Properties.cosmicRuneId, org.powerbot.script.rt4.Bank.Amount.ALL);
                    Condition.wait(() -> !ctx.inventory.select().id(Properties.cosmicRuneId).isEmpty(), random(100), 10);
                }


            } else if (ctx.inventory.select().id(id).isEmpty()) {                           //no more to enchant items in inventory
                if(ctx.bank.select().id(id).isEmpty()) {                                    //check if there are some in bank
                    System.err.println("No more selected Items to Enchant!");
                    Properties.running = false;
                } else {                                                                    //if there are items in bank, withdraw them
                    ctx.bank.withdraw(id, 27);
                    Condition.wait(() -> !ctx.inventory.select().id(id).isEmpty(),
                            random(100), 5);
                }


            } else if(ctx.bank.opened()){
                ctx.bank.close();
                Condition.wait(() -> !ctx.bank.opened(), random(100), 5);
            }
        }
    }
}
