'use strict';

angular.module('ontimeApp')
	.controller('AllocationDeleteController', function($scope, $uibModalInstance, entity, Allocation) {

        $scope.allocation = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Allocation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
