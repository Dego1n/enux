package com.gameserver.instance;

import com.gameserver.database.staticdata.Race;
import com.gameserver.instance.loader.*;
import com.gameserver.instance.loader.buylist.BuyListLoader;
import com.gameserver.instance.loader.item.ArmorLoader;
import com.gameserver.instance.loader.item.CurrencyLoader;
import com.gameserver.instance.loader.item.JewelryLoader;
import com.gameserver.instance.loader.item.WeaponsLoader;
import com.gameserver.model.ability.Ability;
import com.gameserver.template.NPC;
import com.gameserver.template.buylist.BuyList;
import com.gameserver.template.item.BaseItem;
import com.gameserver.template.stats.BaseStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Map;

public class DataEngine {

    private static final Logger log = LoggerFactory.getLogger(DataEngine.class);

    private static DataEngine _instance;

    public static DataEngine getInstance()
    {
        if(_instance == null)
            _instance = new DataEngine();

        return _instance;
    }

    private final List<NPC> npcList;
    private final List<BaseStats> baseStats;
    private final List<BaseItem> items;
    private final Map<Integer, Integer> experienceTable;
    private final List<Ability> abilities;
    private final List<BuyList> buyLists;

    private DataEngine()
    {
        baseStats = PCBaseStatsLoader.LoadPCBaseStats();
        log.info("Loaded {} PC Base Stats", baseStats.size());
        npcList = NPCLoader.LoadNpcData();
        log.info("Loaded {} NPC Data", npcList.size());
        items = WeaponsLoader.LoadWeapons();
        log.info("Loaded {} Weapon items", items.size());
        List<BaseItem> armor = ArmorLoader.LoadArmor();
        log.info("Loaded {} Armor items", armor.size());
        items.addAll(armor);
        List<BaseItem> jewelry = JewelryLoader.LoadJewelry();
        log.info("Loaded {} Jewelry items", armor.size());
        items.addAll(jewelry);
        List<BaseItem> currency = CurrencyLoader.LoadCurrency();
        log.info("Loaded {} Currency items", currency.size());
        items.addAll(currency);
        log.info("Loaded {} total items", items.size());
        buyLists = BuyListLoader.LoadBuyListData();
        log.info("Loaded {} BuyLists", buyLists.size());
        experienceTable = ExperienceLoader.loadExperienceTable();
        log.info("Loaded {} levels", experienceTable.size());
        abilities = AbilitiesLoader.loadAbilities();
        log.info("Loaded {} abilities", abilities.size());
    }


    public NPC getNPCById(int id) {
        for(NPC npc : npcList)
        {
            if(npc.getId() == id)
                return npc;
        }
        return null;
    }

    public BaseItem getItemById(int id) {
        for(BaseItem item : items)
        {
            if(item.getId() == id)
                return item;
        }
        return null;
    }

    public BaseStats GetPCBaseStatsByRace(Race race)
    {
        for(BaseStats pcbs : baseStats)
        {
            if(pcbs.getRace() == race)
                return pcbs;
        }

        return null;
    }

    public int getLevelByExperience(int experience)
    {
        if( experience <= 0)
            return 1;
        int resultLevel = 1;
        for(Map.Entry<Integer,Integer> level : this.experienceTable.entrySet())
        {
            if(experience >= level.getValue())
            {
                resultLevel = level.getKey();
            }
            if(level.getValue() > experience)
                break;
        }

        return resultLevel;
    }

    public Ability getAbilityById(int id)
    {
        for(Ability ability : abilities)
        {
            if(ability.getId() == id)
                return ability;
        }
        return null;
    }

    public BuyList getBuyListById(int id)
    {
        for(BuyList buyList : buyLists)
        {
            if(buyList.getBuyListId() == id)
            {
                return buyList;
            }
        }
        return null;
    }
}
