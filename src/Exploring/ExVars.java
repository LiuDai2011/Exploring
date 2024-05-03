package Exploring;

import Exploring.ui.ExResearchDialog;
import Exploring.util.Pair;
import Exploring.util.Tuple;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.Liquid;
import mindustry.type.LiquidStack;

import java.util.HashMap;

public class ExVars {
    public static HashMap<
            Item,
            Tuple<
                    Tuple<                          //inputs
                            Float,                  //power
                            ItemStack[],            //items
                            LiquidStack[]>,         //liquids
                    Tuple<                          //outputs
                            Float,                  //power
                            ItemStack[],            //items
                            LiquidStack[]>,         //liquids
                    Pair<
                            Integer,                //level
                            HashMap<
                                    Liquid,
                                    Pair<
                                            Float,  //amount
                                            Float   //multiplier
                                            >
                                    >
                            >
                    >
            > molecularWeight;

    public static ExResearchDialog techUI;

    public static void init() {
        techUI = new ExResearchDialog();
    }
}
