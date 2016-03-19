'use strict';

angular.module('consultorioOnlineUiApp')
    .controller('DrugController', function ($scope, $state, Drug, DrugSearch) {

        $scope.drugs = [];
        $scope.loadAll = function() {
            Drug.query(function(result) {
               $scope.drugs = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            DrugSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.drugs = result;
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
            $scope.drug = {
                code: null,
                name: null,
                description: null,
                id: null
            };
        };
    });
