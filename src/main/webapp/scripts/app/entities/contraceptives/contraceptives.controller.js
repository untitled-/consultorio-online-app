'use strict';

angular.module('consultorioOnlineAppApp')
    .controller('ContraceptivesController', function ($scope, $state, Contraceptives, ContraceptivesSearch) {

        $scope.contraceptivess = [];
        $scope.loadAll = function() {
            Contraceptives.query(function(result) {
               $scope.contraceptivess = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            ContraceptivesSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.contraceptivess = result;
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
            $scope.contraceptives = {
                name: null,
                id: null
            };
        };
    });
