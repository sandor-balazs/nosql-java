'use strict';

angular.module('ontimeApp').controller('AllocationDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Allocation',
        function($scope, $stateParams, $uibModalInstance, entity, Allocation) {

        $scope.allocation = entity;
        $scope.load = function(id) {
            Allocation.get({id : id}, function(result) {
                $scope.allocation = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('ontimeApp:allocationUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.allocation.id != null) {
                Allocation.update($scope.allocation, onSaveSuccess, onSaveError);
            } else {
                Allocation.save($scope.allocation, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
