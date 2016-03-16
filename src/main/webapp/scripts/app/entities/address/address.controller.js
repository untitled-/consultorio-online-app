'use strict';

angular.module('consultorioOnlineUiApp')
    .controller('AddressController', function ($scope, $state, Address, AddressSearch) {

        $scope.addresss = [];
        $scope.loadAll = function() {
            Address.query(function(result) {
               $scope.addresss = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            AddressSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.addresss = result;
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
            $scope.address = {
                street: null,
                apt: null,
                city: null,
                zip: null,
                country: null,
                id: null
            };
        };
    });
