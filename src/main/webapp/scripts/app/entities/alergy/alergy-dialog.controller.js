'use strict';

angular.module('consultorioOnlineAppApp').controller('AlergyDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Alergy', 'PathologicBkg',
        function($scope, $stateParams, $uibModalInstance, entity, Alergy, PathologicBkg) {

        $scope.alergy = entity;
        $scope.pathologicbkgs = PathologicBkg.query();
        $scope.load = function(id) {
            Alergy.get({id : id}, function(result) {
                $scope.alergy = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('consultorioOnlineAppApp:alergyUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.alergy.id != null) {
                Alergy.update($scope.alergy, onSaveSuccess, onSaveError);
            } else {
                Alergy.save($scope.alergy, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
