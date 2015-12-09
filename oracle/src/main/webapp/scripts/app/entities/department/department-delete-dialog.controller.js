'use strict';

angular.module('ontimeApp')
	.controller('DepartmentDeleteController', function($scope, $uibModalInstance, entity, Department) {

        $scope.department = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Department.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
