'use strict';

angular.module('consultorioOnlineAppApp')
    .controller('ContactDetailController', function ($scope, $rootScope, $stateParams, entity, Contact, Patient) {
        $scope.contact = entity;
        $scope.load = function (id) {
            Contact.get({id: id}, function(result) {
                $scope.contact = result;
            });
        };
        var unsubscribe = $rootScope.$on('consultorioOnlineAppApp:contactUpdate', function(event, result) {
            $scope.contact = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
