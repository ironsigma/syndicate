<?php
namespace Cyniris\SyndicateBundle\Tests\Entity;

use Symfony\Bundle\FrameworkBundle\Test\WebTestCase;
use Symfony\Bundle\FrameworkBundle\Console\Application;
use Symfony\Component\Console\Output\NullOutput;
use Symfony\Component\Console\Input\ArrayInput;
use Doctrine\Bundle\DoctrineBundle\Command\DropDatabaseDoctrineCommand;
use Doctrine\Bundle\DoctrineBundle\Command\CreateDatabaseDoctrineCommand;
use Doctrine\Bundle\DoctrineBundle\Command\Proxy\CreateSchemaDoctrineCommand;

use Cyniris\SyndicateBundle\Entity\Preference;
use Cyniris\SyndicateBundle\Entity\Feed;
use Cyniris\SyndicateBundle\Entity\Folder;

class PreferenceTest extends DoctrineTestCase {
    protected $feed;
    protected $folder;

    public function setUp() {
        parent::setUp();
        $this->feed = new Feed();
        $this->feed->setName("TechMunch");
        $this->feed->setUrl('http://techmuch.com/');
        $this->feed->setUpdateFrequency(10);
        $this->em->persist($this->feed);

        $this->folder = new Folder();
        $this->folder->setName('My Folder');
        $this->em->persist($this->folder);
    }

    public function testPersist() {
        $now = new \DateTime('Today');
        $pref = new Preference();
        $pref->setActive(true);
        $pref->setFolderLevel(true);
        $pref->setDisplayText(true);
        $pref->setDisplayCount(50);
        $pref->setSortOrder('ASC');
        $pref->setFeed($this->feed);
        $pref->setFolder($this->folder);

        $this->em->persist($pref);
        $this->em->flush();

        $this->assertNotNull($pref->getId());

        $fetched = $this->em
            ->getRepository('CynirisSyndicateBundle:Preference')
            ->find($pref->getId());

        $this->assertNotNull($fetched);
        $this->assertTrue($fetched->getActive());
        $this->assertTrue($fetched->getFolderLevel());
        $this->assertTrue($fetched->getDisplayText());
        $this->assertEquals(50, $fetched->getDisplayCount());
        $this->assertEquals('ASC', $fetched->getSortOrder());
        $this->assertEquals($this->feed, $fetched->getFeed());
        $this->assertEquals($this->folder, $fetched->getFolder());
    }
}

