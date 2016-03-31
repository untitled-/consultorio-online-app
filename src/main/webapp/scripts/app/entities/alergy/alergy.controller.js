'use strict';

angular.module('consultorioOnlineAppApp')
    .controller('AlergyController', function ($scope, $state, Alergy, AlergySearch) {

        $scope.alergys = [];
        $scope.loadAll = function() {
            Alergy.query(function(result) {
               $scope.alergys = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            AlergySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.alergys = result;
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
            $scope.alergy = {
                code: null,
                name: null,
                description: null,
                id: null
            };
        };
    });
