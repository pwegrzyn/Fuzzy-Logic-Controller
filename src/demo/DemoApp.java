package demo;

import net.sourceforge.jFuzzyLogic.rule.FuzzyRuleSet;

import java.util.Random;

public class DemoApp {

    private final int horizontalDistanceToMob;
    private final int verticalPositionOfWizard;
    private final int verticalPositionOfMob;
    private FuzzyRuleSet fuzzyRuleSet;

    public DemoApp(FuzzyRuleSet fuzzyRuleSet, int horizontalDistanceToMob, int verticalPositionOfWizard, int verticalPositionOfMob) {
        this.fuzzyRuleSet = fuzzyRuleSet;
        this.horizontalDistanceToMob = horizontalDistanceToMob;
        this.verticalPositionOfWizard = verticalPositionOfWizard;
        this.verticalPositionOfMob = verticalPositionOfMob;
        this.fuzzyRuleSet.setVariable("horizontalDistanceToMob", horizontalDistanceToMob);
        this.fuzzyRuleSet.setVariable("verticalPositionOfWizard", verticalPositionOfWizard);
        this.fuzzyRuleSet.setVariable("verticalPositionOfMob", verticalPositionOfMob);
        this.fuzzyRuleSet.evaluate();
    }

    public void run() {
        Configuration cfg = new Configuration();
        cfg.setTargetHeight(312);
        cfg.setTargetWidth(500);
        cfg.setTargetFps(9001);
        cfg.setKeyBinds(new KeybindSet());
        GlobalConfig.setGlobalConfig(cfg);
        RenderManager renderManager = new RenderManager();
        InputManager inputManager = new InputManager();
        AreaManager areaManager = new AreaManager();
        renderManager.setInputManager(inputManager);
        renderManager.initialSetup();
        ImageLoader imageLoader = new ImageLoader(renderManager.getMainCanvas());
        CollisionEngine.setStrategy("circle");
        new Thread(() -> {
            Player player = new Player(0, 50);
            player.setInputManager(inputManager);
            Area area = new Area("Knowhere");
            area.setTexture(imageLoader.fetchImage("resources/grass_land_new.png"));
            area.setSize(1471, 100);
            area.addEntity(player);
            player.setArea(area);
            areaManager.setCurrentArea(area);
            renderManager.startRendering(areaManager);
            MobFactory factory = new MobFactory();
            Random random = new Random();
            boolean firstMobPassingDone = false;
            for(;;) {
                int randomLocX, randomLocY;
                if(!firstMobPassingDone) {
                    randomLocX = this.horizontalDistanceToMob;
                    randomLocY = this.verticalPositionOfMob;
                    firstMobPassingDone = true;
                } else {
                    randomLocX = random.nextInt(170) + 30 + (int)player.getXPos();
                    randomLocY = random.nextInt(90) + 10;
                }
                RangedMob boss = (RangedMob) factory.createRangedBoss(randomLocX, randomLocY);
                area.addEntity(boss);
                while(player.getXPos() < randomLocX) {
                    this.fuzzyRuleSet.setVariable("verticalPositionOfWizard", player.getYPos());
                    this.fuzzyRuleSet.setVariable("verticalPositionOfMob", randomLocY);
                    this.fuzzyRuleSet.setVariable("horizontalDistanceToMob", randomLocX - player.getXPos());
                    this.fuzzyRuleSet.evaluate();
                    int wizardVerticalSpeed = (int)this.fuzzyRuleSet.getVariable("verticalSpeed").getLatestDefuzzifiedValue();
                    player.setVerticalSpeed(wizardVerticalSpeed);
                    /*try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                }
            }
        }).start();
    }

}
