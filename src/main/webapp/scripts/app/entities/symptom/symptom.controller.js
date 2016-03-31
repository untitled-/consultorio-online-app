'use strict';

angular.module('consultorioOnlineAppApp')
    .controller('SymptomController', function ($scope, $state, Symptom, SymptomSearch) {

        $scope.symptoms = [];
        $scope.loadAll = function() {
            Symptom.query(function(result) {
               $scope.symptoms = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            SymptomSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.symptoms = result;
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
            $scope.symptom = {
                code: null,
                name: null,
                description: null,
                id: null
            };
        };
    });
