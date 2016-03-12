'use strict';

angular.module('consultorioOnlineUiApp').controller('ContactDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Contact', 'Patient',
        function($scope, $stateParams, $uibModalInstance, entity, Contact, Patient) {

        $scope.contact = entity;
        $scope.patients = Patient.query();
        $scope.load = function(id) {
            Contact.get({id : id}, function(result) {
                $scope.contact = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('consultorioOnlineUiApp:contactUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.contact.id != null) {
                Contact.update($scope.contact, onSaveSuccess, onSaveError);
            } else {
                Contact.save($scope.contact, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
