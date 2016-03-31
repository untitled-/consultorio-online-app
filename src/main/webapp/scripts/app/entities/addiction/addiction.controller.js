'use strict';

angular.module('consultorioOnlineAppApp')
    .controller('AddictionController', function ($scope, $state, Addiction, AddictionSearch) {

        $scope.addictions = [];
        $scope.loadAll = function() {
            Addiction.query(function(result) {
               $scope.addictions = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            AddictionSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.addictions = result;
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
            $scope.addiction = {
                code: null,
                name: null,
                description: null,
                id: null
            };
        };
    });
