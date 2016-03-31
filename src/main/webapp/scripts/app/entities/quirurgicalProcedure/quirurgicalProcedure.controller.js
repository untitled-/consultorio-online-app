'use strict';

angular.module('consultorioOnlineAppApp')
    .controller('QuirurgicalProcedureController', function ($scope, $state, QuirurgicalProcedure, QuirurgicalProcedureSearch) {

        $scope.quirurgicalProcedures = [];
        $scope.loadAll = function() {
            QuirurgicalProcedure.query(function(result) {
               $scope.quirurgicalProcedures = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            QuirurgicalProcedureSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.quirurgicalProcedures = result;
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
            $scope.quirurgicalProcedure = {
                code: null,
                name: null,
                description: null,
                id: null
            };
        };
    });
