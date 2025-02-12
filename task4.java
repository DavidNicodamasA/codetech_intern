import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.util.List;

public class RecommendationSystem {
    public static void main(String[] args) {
        try {
            // Load data from CSV file
            DataModel model = new FileDataModel(new File("data.csv"));
            
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
            
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);
            
            Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
            
            List<RecommendedItem> recommendations = recommender.recommend(1, 3);
            
            System.out.println("Recommended Items for User 1:");
            for (RecommendedItem recommendation : recommendations) {
                System.out.println("Item: " + recommendation.getItemID() + " - Score: " + recommendation.getValue());
            }
            
            RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
            double score = evaluator.evaluate(recommender, null, model, 0.9, 1.0);
            System.out.println("Evaluation Score: " + score);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
