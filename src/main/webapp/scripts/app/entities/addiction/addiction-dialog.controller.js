'use strict';

angular.module('consultorioOnlineAppApp').controller('AddictionDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Addiction', 'PathologicBkg',
        function($scope, $stateParams, $uibModalInstance, entity, Addiction, PathologicBkg) {

        $scope.addiction = entity;
        $scope.pathologicbkgs = PathologicBkg.query();
        $scope.load = function(id) {
            Addiction.get({id : id}, function(result) {
                $scope.addiction = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('consultorioOnlineAppApp:addictionUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.addiction.id != null) {
                Addiction.update($scope.addiction, onSaveSuccess, onSaveError);
            } else {
                Addiction.save($scope.addiction, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
