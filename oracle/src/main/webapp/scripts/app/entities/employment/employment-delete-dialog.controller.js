'use strict';

angular.module('ontimeApp')
	.controller('EmploymentDeleteController', function($scope, $uibModalInstance, entity, Employment) {

        $scope.employment = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Employment.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
