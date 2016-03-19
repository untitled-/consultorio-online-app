'use strict';

angular.module('consultorioOnlineUiApp')
    .controller('ImmunizationController', function ($scope, $state, Immunization, ImmunizationSearch) {

        $scope.immunizations = [];
        $scope.loadAll = function() {
            Immunization.query(function(result) {
               $scope.immunizations = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            ImmunizationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.immunizations = result;
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
            $scope.immunization = {
                code: null,
                name: null,
                description: null,
                id: null
            };
        };
    });
