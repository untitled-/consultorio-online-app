'use strict';

angular.module('consultorioOnlineAppApp')
    .controller('ContactController', function ($scope, $state, Contact, ContactSearch) {

        $scope.contacts = [];
        $scope.loadAll = function() {
            Contact.query(function(result) {
               $scope.contacts = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            ContactSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.contacts = result;
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
            $scope.contact = {
                contactType: null,
                value: null,
                id: null
            };
        };
    });
