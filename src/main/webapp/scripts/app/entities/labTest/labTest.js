'use strict';

angular.module('consultorioOnlineUiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('labTest', {
                parent: 'entity',
                url: '/labTests',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineUiApp.labTest.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/labTest/labTests.html',
                        controller: 'LabTestController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('labTest');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('labTest.detail', {
                parent: 'entity',
                url: '/labTest/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineUiApp.labTest.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/labTest/labTest-detail.html',
                        controller: 'LabTestDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('labTest');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'LabTest', function($stateParams, LabTest) {
                        return LabTest.get({id : $stateParams.id});
                    }]
                }
            })
            .state('labTest.new', {
                parent: 'labTest',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/labTest/labTest-dialog.html',
                        controller: 'LabTestDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    description: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('labTest', null, { reload: true });
                    }, function() {
                        $state.go('labTest');
                    })
                }]
            })
            .state('labTest.edit', {
                parent: 'labTest',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/labTest/labTest-dialog.html',
                        controller: 'LabTestDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['LabTest', function(LabTest) {
                                return LabTest.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('labTest', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('labTest.delete', {
                parent: 'labTest',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/labTest/labTest-delete-dialog.html',
                        controller: 'LabTestDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['LabTest', function(LabTest) {
                                return LabTest.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('labTest', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
