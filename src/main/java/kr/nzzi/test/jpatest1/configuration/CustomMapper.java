package kr.nzzi.test.jpatest1.configuration;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class CustomMapper extends ModelMapper {

    private static class MapperHelper {
        private static final CustomMapper INSTANCE = new CustomMapper();
    }

    private CustomMapper() {
        super
            .getConfiguration()
            .setFieldMatchingEnabled(true)
            .setMatchingStrategy(MatchingStrategies.LOOSE)
            .setPropertyCondition(Conditions.isNotNull())
            .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
    }

    public static CustomMapper getInstance() {
        return MapperHelper.INSTANCE;
    }
}
