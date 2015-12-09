'use strict';

angular.module('ontimeApp').controller('StatusDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Status',
        function($scope, $stateParams, $uibModalInstance, entity, Status) {

        $scope.status = entity;
        $scope.load = function(id) {
            Status.get({id : id}, function(result) {
                $scope.status = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('ontimeApp:statusUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.status.id != null) {
                Status.update($scope.status, onSaveSuccess, onSaveError);
            } else {
                Status.save($scope.status, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
