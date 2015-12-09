'use strict';

angular.module('ontimeApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('milestone', {
                parent: 'entity',
                url: '/milestones',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ontimeApp.milestone.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/milestone/milestones.html',
                        controller: 'MilestoneController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('milestone');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('milestone.detail', {
                parent: 'entity',
                url: '/milestone/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ontimeApp.milestone.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/milestone/milestone-detail.html',
                        controller: 'MilestoneDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('milestone');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Milestone', function($stateParams, Milestone) {
                        return Milestone.get({id : $stateParams.id});
                    }]
                }
            })
            .state('milestone.new', {
                parent: 'milestone',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/milestone/milestone-dialog.html',
                        controller: 'MilestoneDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    appOrder: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('milestone', null, { reload: true });
                    }, function() {
                        $state.go('milestone');
                    })
                }]
            })
            .state('milestone.edit', {
                parent: 'milestone',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/milestone/milestone-dialog.html',
                        controller: 'MilestoneDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Milestone', function(Milestone) {
                                return Milestone.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('milestone', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('milestone.delete', {
                parent: 'milestone',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/milestone/milestone-delete-dialog.html',
                        controller: 'MilestoneDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Milestone', function(Milestone) {
                                return Milestone.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('milestone', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
