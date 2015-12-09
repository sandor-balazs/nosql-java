'use strict';

angular.module('ontimeApp')
	.controller('ProjectRegionDeleteController', function($scope, $uibModalInstance, entity, ProjectRegion) {

        $scope.projectRegion = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ProjectRegion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
