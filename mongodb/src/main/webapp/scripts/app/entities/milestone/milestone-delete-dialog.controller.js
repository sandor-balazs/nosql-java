'use strict';

angular.module('ontimeApp')
	.controller('MilestoneDeleteController', function($scope, $uibModalInstance, entity, Milestone) {

        $scope.milestone = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Milestone.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
