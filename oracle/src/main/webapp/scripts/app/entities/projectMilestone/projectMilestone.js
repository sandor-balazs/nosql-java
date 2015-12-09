'use strict';

angular.module('ontimeApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('projectMilestone', {
                parent: 'entity',
                url: '/projectMilestones',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ontimeApp.projectMilestone.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/projectMilestone/projectMilestones.html',
                        controller: 'ProjectMilestoneController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('projectMilestone');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('projectMilestone.detail', {
                parent: 'entity',
                url: '/projectMilestone/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ontimeApp.projectMilestone.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/projectMilestone/projectMilestone-detail.html',
                        controller: 'ProjectMilestoneDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('projectMilestone');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ProjectMilestone', function($stateParams, ProjectMilestone) {
                        return ProjectMilestone.get({id : $stateParams.id});
                    }]
                }
            })
            .state('projectMilestone.new', {
                parent: 'projectMilestone',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/projectMilestone/projectMilestone-dialog.html',
                        controller: 'ProjectMilestoneDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    planned: null,
                                    actual: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('projectMilestone', null, { reload: true });
                    }, function() {
                        $state.go('projectMilestone');
                    })
                }]
            })
            .state('projectMilestone.edit', {
                parent: 'projectMilestone',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/projectMilestone/projectMilestone-dialog.html',
                        controller: 'ProjectMilestoneDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ProjectMilestone', function(ProjectMilestone) {
                                return ProjectMilestone.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('projectMilestone', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('projectMilestone.delete', {
                parent: 'projectMilestone',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/projectMilestone/projectMilestone-delete-dialog.html',
                        controller: 'ProjectMilestoneDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ProjectMilestone', function(ProjectMilestone) {
                                return ProjectMilestone.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('projectMilestone', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
