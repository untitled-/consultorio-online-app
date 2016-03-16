'use strict';

angular.module('consultorioOnlineUiApp')
    .controller('DiseaseController', function ($scope, $state, Disease, DiseaseSearch) {

        $scope.diseases = [];
        $scope.loadAll = function() {
            Disease.query(function(result) {
               $scope.diseases = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            DiseaseSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.diseases = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.disease = {
                code: null,
                name: null,
                description: null,
                type: null,
                isCongenital: null,
                isInfectious: null,
                id: null
            };
        };
    });
