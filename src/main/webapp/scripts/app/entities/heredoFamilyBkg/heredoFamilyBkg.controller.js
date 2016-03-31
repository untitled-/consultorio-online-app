'use strict';

angular.module('consultorioOnlineAppApp')
    .controller('HeredoFamilyBkgController', function ($scope, $state, HeredoFamilyBkg, HeredoFamilyBkgSearch) {

        $scope.heredoFamilyBkgs = [];
        $scope.loadAll = function() {
            HeredoFamilyBkg.query(function(result) {
               $scope.heredoFamilyBkgs = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            HeredoFamilyBkgSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.heredoFamilyBkgs = result;
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
            $scope.heredoFamilyBkg = {
                observation: null,
                id: null
            };
        };
    });
