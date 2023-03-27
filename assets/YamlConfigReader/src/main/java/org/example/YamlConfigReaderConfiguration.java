package org.example;

import io.dropwizard.core.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;

public class YamlConfigReaderConfiguration extends Configuration {

    public static final class MyMetrics{

        private String myArg;
        private List<String> myList;
        private List<List<String>> myListOfLists;

        private Map<String, String> myMap;
        private Map<String, List<Integer>> myMapOfLists;

        @NotBlank
        @JsonProperty
        public String getMyArg(){
            return myArg;
        }

        @NotNull
        @NotEmpty
        @JsonProperty
        public List<String> getMyList(){
            return myList;
        }

        @NotNull
        @NotEmpty
        @JsonProperty
        public List<List<String>> getMyListOfLists(){
            return myListOfLists;
        }

        @NotNull
        @NotEmpty
        @JsonProperty
        public Map<String, String> getMyMap(){
            return myMap;
        }

        @NotNull
        @NotEmpty
        @JsonProperty
        public Map<String, List<Integer>> getMyMapOfLists(){
            return myMapOfLists;
        }
    }

    private MyMetrics myMetrics;

    @Valid
    public MyMetrics getMyMetrics(){
        return myMetrics;
    }

}
