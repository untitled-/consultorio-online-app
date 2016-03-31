'use strict';

angular.module('consultorioOnlineAppApp')
    .controller('PathologicBkgController', function ($scope, $state, PathologicBkg, PathologicBkgSearch) {

        $scope.pathologicBkgs = [];
        $scope.loadAll = function() {
            PathologicBkg.query(function(result) {
               $scope.pathologicBkgs = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            PathologicBkgSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pathologicBkgs = result;
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
            $scope.pathologicBkg = {
                observations: null,
                id: null
            };
        };
    });
