'use strict';

angular.module('ontimeApp')
	.controller('ProjectCountryMilestoneDeleteController', function($scope, $uibModalInstance, entity, ProjectCountryMilestone) {

        $scope.projectCountryMilestone = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ProjectCountryMilestone.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
