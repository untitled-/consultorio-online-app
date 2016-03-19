'use strict';

angular.module('consultorioOnlineUiApp')
    .controller('TraumaController', function ($scope, $state, Trauma, TraumaSearch) {

        $scope.traumas = [];
        $scope.loadAll = function() {
            Trauma.query(function(result) {
               $scope.traumas = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            TraumaSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.traumas = result;
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
            $scope.trauma = {
                code: null,
                name: null,
                description: null,
                id: null
            };
        };
    });
