'use strict';

angular.module('consultorioOnlineUiApp')
    .controller('NonPathologicBkgController', function ($scope, $state, NonPathologicBkg, NonPathologicBkgSearch) {

        $scope.nonPathologicBkgs = [];
        $scope.loadAll = function() {
            NonPathologicBkg.query(function(result) {
               $scope.nonPathologicBkgs = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            NonPathologicBkgSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.nonPathologicBkgs = result;
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
            $scope.nonPathologicBkg = {
                housing: null,
                hasZoonosis: null,
                zoonosisDesc: null,
                isOvercrowded: null,
                overcrowdingDesc: null,
                isFeedingBalanced: null,
                feedingDesc: null,
                hygieneDesc: null,
                id: null
            };
        };
    });
