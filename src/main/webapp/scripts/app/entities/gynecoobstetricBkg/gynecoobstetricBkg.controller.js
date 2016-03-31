'use strict';

angular.module('consultorioOnlineAppApp')
    .controller('GynecoobstetricBkgController', function ($scope, $state, GynecoobstetricBkg, GynecoobstetricBkgSearch) {

        $scope.gynecoobstetricBkgs = [];
        $scope.loadAll = function() {
            GynecoobstetricBkg.query(function(result) {
               $scope.gynecoobstetricBkgs = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            GynecoobstetricBkgSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.gynecoobstetricBkgs = result;
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
            $scope.gynecoobstetricBkg = {
                menarche: null,
                begginingSexualLife: null,
                pregnanciesNumber: null,
                miscarriagesNumber: null,
                cSectionsNumber: null,
                pregnancyDetails: null,
                latestPapTest: null,
                latestPapTestDetails: null,
                latestMammography: null,
                latestMammographyDetails: null,
                usesContraceptives: null,
                hasMenopause: null,
                id: null
            };
        };
    });
