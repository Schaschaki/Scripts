package Enchanter;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.Game;
import org.powerbot.script.rt4.Magic;

import static Superior.Utilities.breathe;
import static Superior.Utilities.random;

public class Enchant extends Task<ClientContext> {

    public Enchant(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {    //Runes & Items to enchant necessary
        if(!Properties.rightStaff) return false;
        else return !ctx.inventory.select().id(Properties.SelectedItem.SELECTED_ITEM.id()).isEmpty()
                    && !ctx.inventory.select().id(Properties.cosmicRuneId).isEmpty()
                    && !ctx.bank.opened();
    }

    @Override
    public void execute() {

        Magic.MagicSpell spell = Properties.SelectedItem.SELECTED_ITEM.spell;
        int id = Properties.SelectedItem.SELECTED_ITEM.id();

        if (!(Game.Tab.MAGIC == ctx.game.tab()) && !ctx.magic.casting(spell)) {             //casting no spell && Magic Tab not open
            ctx.game.tab(Game.Tab.MAGIC);
            Condition.wait(() -> ctx.game.tab(Game.Tab.MAGIC), random(100),5);
        } else if(Game.Tab.MAGIC == ctx.game.tab() && !ctx.magic.casting(spell)){           //casting no spell && Magic Tab open
            ctx.magic.cast(spell);
            Condition.wait(() -> ctx.magic.casting(spell), random(80), 10);
        } else if(Game.Tab.INVENTORY == ctx.game.tab() && ctx.magic.casting(spell)) {       //casting spell && Inventory Tab open
            ctx.inventory.select().id(id).poll().interact("Cast");
            Condition.wait(() -> ctx.game.tab() == Game.Tab.MAGIC);
            breathe(random(50));
        } else if(Game.Tab.MAGIC == ctx.game.tab() && ctx.magic.casting(spell)) {           //casting spell && Magic Tab open
            ctx.game.tab(Game.Tab.INVENTORY);
            Condition.wait(() -> ctx.game.tab() == Game.Tab.INVENTORY, random(80), 10);
        } else if(ctx.magic.casting(spell) && ctx.inventory.select().id(id).isEmpty()) {
            if(!ctx.inventory.isEmpty()) ctx.inventory.poll().interact("Cancel");
            else{
                //This is never meant to happen, just if something unexpected happens - this might unstuck it
                ctx.objects.nearest().poll().interact("Cancel");
            }
        }
    }
}
