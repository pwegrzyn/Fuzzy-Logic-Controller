import demo.DemoApp;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.FuzzyRuleSet;

public class Main {

    public static void main(String[] args) {
        try {
            String fileName = args[0];
            int horizontalDistanceToMob = Integer.parseInt(args[1]);
            int verticalPositionOfWizard = Integer.parseInt(args[2]);
            int verticalPositionOfMob = Integer.parseInt(args[3]);
            FIS fis = FIS.load(fileName,false);
            FuzzyRuleSet fuzzyRuleSet = fis.getFuzzyRuleSet();
            fuzzyRuleSet.chart();
            DemoApp app = new DemoApp(fuzzyRuleSet, horizontalDistanceToMob, verticalPositionOfWizard, verticalPositionOfMob);
            app.run();

        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Too few arguments have been passed!");
        } catch (NumberFormatException ex) {
            System.out.println("Wrong parameter format!");
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}