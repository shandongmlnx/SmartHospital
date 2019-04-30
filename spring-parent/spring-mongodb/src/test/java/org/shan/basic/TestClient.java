package org.shan.basic;

import com.sun.nio.zipfs.ZipInfo;

import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Created by amanda.shan on 2019/1/21.
 */
public class TestClient {

    public static void main(String[] args) {

        Aggregation agg = newAggregation(
                group("state", "city")
                        .sum("population").as("pop"),
                sort(ASC, "pop", "state", "city"),
                group("state")
                        .last("city").as("biggestCity")
                        .last("pop").as("biggestPop")
                        .first("city").as("smallestCity")
                        .first("pop").as("smallestPop"),
                project()
                        .and("state").previousOperation()
                        .and("biggestCity")
                        .nested(bind("name", "biggestCity").and("population", "biggestPop"))
                        .and("smallestCity")
                        .nested(bind("name", "smallestCity").and("population", "smallestPop")),
                sort(ASC, "state")
        );

        TypedAggregation<ZipInfo> agg1 = newAggregation(ZipInfo.class,
                group("state").sum("population").as("totalPop"),
                sort(ASC, previousOperation(), "totalPop"),
                match(where("totalPop").gte(10 * 1000 * 1000))
        );

        TypedAggregation<String> agg2 = newAggregation(String.class,
                project("name", "netPrice")
                        .and("netPrice").plus(1).as("netPricePlus1")
                        .and("netPrice").minus(1).as("netPriceMinus1")
                        .and("netPrice").multiply(1.19).as("grossPrice")
                        .and("netPrice").divide(2).as("netPriceDiv2")
                        .and("spaceUnits").mod(2).as("spaceUnitsMod2")
        );

        TypedAggregation<String> agg3= newAggregation(String.class,
                project("name", "netPrice")
                        .andExpression("netPrice + 1").as("netPricePlus1")
                        .andExpression("netPrice - 1").as("netPriceMinus1")
                        .andExpression("netPrice / 2").as("netPriceDiv2")
                        .andExpression("netPrice * 1.19").as("grossPrice")
                        .andExpression("spaceUnits % 2").as("spaceUnitsMod2")
                        .andExpression("(netPrice * 0.8  + 1.2) * 1.19").as("grossPriceIncludingDiscountAndCharge")

        );


        System.out.println(agg3.toString());
    }
}
